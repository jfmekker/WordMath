namespace WordMath
{
    public class Number
    {
        public long Value { get; }
        public string[] Words { get; }

        public Number( long value )
        {
            this.Value = value;

            List<NumberNode> nodes = [];

            if ( value == 0 )
            {
                nodes.Add( ZeroNode.Zero( ) );
            }
            else if ( value < 0 )
            {
                nodes.Add( SignNode.Negative( ) );
                value *= -1;
            }

            while ( value > 0 )
            {
                ModifierNode? modifier = ModifierNode.FromValue( value, out long group, out long remaining );

                (SingleDigitNode, HundredNode)? hundreds = HundredNode.FromValue( group, out group );
                if ( hundreds is not null )
                {
                    nodes.Add( hundreds.Value.Item1 );
                    nodes.Add( hundreds.Value.Item2 );
                }

                if ( TeensNode.FromValue( group ) is TeensNode teens )
                {
                    nodes.Add( teens );
                }
                else
                {
                    if ( TensNode.FromValue( group, out group ) is TensNode tens )
                    {
                        nodes.Add( tens );
                    }

                    if ( SingleDigitNode.FromValue( group ) is SingleDigitNode single )
                    {
                        nodes.Add( single );
                    }
                }

                if ( modifier is not null )
                {
                    nodes.Add( modifier );
                }

                value = remaining;
            }

            this.Words = nodes.Select( n => n.Word ).ToArray( );
        }

        public Number( string words ) :
            this( words.Split( ' ', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries ) )
        { }

        public Number( string[] words )
        {
            if ( words.Length == 0 )
            {
                throw new ArgumentException( "Must include at least one word", nameof( words ) );
            }

            this.Words = words;

            Queue<NumberNode> nodes = [];
            foreach ( string word in words )
            {
                NumberNode? node =
                    (NumberNode?)SignNode.FromWord( word ) ??
                    (NumberNode?)ZeroNode.FromWord( word ) ??
                    (NumberNode?)SingleDigitNode.FromWord( word ) ??
                    (NumberNode?)TeensNode.FromWord( word ) ??
                    (NumberNode?)TensNode.FromWord( word ) ??
                    (NumberNode?)HundredNode.FromWord( word ) ??
                    (NumberNode?)ModifierNode.FromWord( word ) ??
                    throw new ArgumentException(
                        $"Could not parse word into any node type: {word}",
                        nameof( words ) );

                nodes.Enqueue( node );
            }

            long sign = 1;
            long? currTotalVal = null;
            long? currGroupVal = null;
            NumberNode? prev = null;
            NumberNode? curr = null;
            while ( nodes.TryDequeue( out curr ) )
            {
                switch ( curr )
                {
                    // Must be alone
                    case ZeroNode:
                    {
                        if ( prev != null ||
                            nodes.Count != 0 )
                        {
                            throw new InvalidOperationException( "Zero must be alone" );
                        }
                        else
                        {
                            currTotalVal = 0;
                        }
                        break;
                    }

                    // Must be first, the only sign, and not alone
                    case SignNode signNode:
                    {
                        if ( prev != null ||
                            nodes.Count == 0 ||
                            nodes.OfType<SignNode>( ).Any( ) )
                        {
                            throw new InvalidOperationException( "Sign must be first, the only sign, and not alone" );
                        }
                        sign = signNode.Value;
                        break;
                    }

                    // Must be first of group value or following a hundred node
                    case TeensNode teensNode:
                    {
                        if ( currGroupVal is not null && prev is not HundredNode )
                        {
                            throw new InvalidOperationException( "Teens node must be first of group value or following a hundred node" );
                        }
                        currGroupVal ??= 0;
                        currGroupVal += teensNode.Value;
                        break;
                    }

                    // Must be first of group or following a tens or hundred node
                    case SingleDigitNode singleNode:
                    {
                        if ( currGroupVal is not null && prev is not (TensNode or HundredNode) )
                        {
                            throw new InvalidOperationException( "Digit node must be first of group value or following a tens or hundred node" );
                        }
                        currGroupVal ??= 0;
                        currGroupVal += singleNode.Value;
                        break;
                    }

                    // Must be first of group or following a hundred node
                    case TensNode tensNode:
                    {
                        if ( currGroupVal is not null && prev is not HundredNode )
                        {
                            throw new InvalidOperationException( "Digit node must be first of group value or following hundred node" );
                        }
                        currGroupVal ??= 0;
                        currGroupVal += tensNode.Value;
                        break;
                    }

                    // Must be following a single digit node
                    case HundredNode:
                    {
                        if ( currGroupVal is null || prev is not SingleDigitNode )
                        {
                            throw new InvalidOperationException( "Hundred node must follow a single digit node" );
                        }
                        currGroupVal *= 100;
                        break;
                    }

                    // Must be following a group value
                    case ModifierNode modNode:
                    {
                        if ( currGroupVal is null )
                        {
                            throw new InvalidOperationException( "Modifier must directly follow a group value" );
                        }
                        else if ( modNode.Value > currTotalVal )
                        {
                            throw new InvalidOperationException( "Modifiers must appear in the correct order" );
                        }
                        currTotalVal ??= 0;
                        currTotalVal += currGroupVal * modNode.Value;
                        currGroupVal = null;
                        break;
                    }

                    default:
                    {
                        throw new InvalidOperationException( $"Unexpected node type: {curr.GetType( ).Name}" );
                    }
                }

                prev = curr;
                curr = null;
            }

            currGroupVal ??= 0;
            currTotalVal ??= 0;
            currTotalVal += currGroupVal;
            currTotalVal *= sign;

            this.Value = currTotalVal.Value;
        }

        public override string ToString( ) => string.Join( ' ', this.Words );
    }

}
