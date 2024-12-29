namespace WordMath
{
    internal abstract class NumberNode
    {
        public string Word { get; }
        public long Value { get; }

        public NumberNode( string word, long value )
        {
            this.Word = word;
            this.Value = value;
        }
    }

    internal class SignNode : NumberNode
    {
        private SignNode( string word, long value ) : base( word, value ) { }

        public static SignNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "positive" => new( word, 1 ),
                "negative" => new( word, -1 ),
                _ => null
            };
        }

        public static SignNode Positive( ) => new( "positive", 1 );
        public static SignNode Negative( ) => new( "negative", -1 );
    }

    internal class ZeroNode : NumberNode
    {
        private ZeroNode( string word, long value ) : base( word, value ) { }

        public static ZeroNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "zero" => new( word, 0 ),
                _ => null,
            };
        }

        public static ZeroNode Zero( ) => new( "zero", 0 );
    }

    internal class SingleDigitNode : NumberNode
    {
        private SingleDigitNode( string word, long value ) : base( word, value ) { }

        public static SingleDigitNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "one" => new( word, 1 ),
                "two" => new( word, 2 ),
                "three" => new( word, 3 ),
                "four" => new( word, 4 ),
                "five" => new( word, 5 ),
                "six" => new( word, 6 ),
                "seven" => new( word, 7 ),
                "eight" => new( word, 8 ),
                "nine" => new( word, 9 ),
                _ => null,
            };
        }

        public static SingleDigitNode? FromValue( long value )
        {
            return value switch {
                1 => new( "one", value ),
                2 => new( "two", value ),
                3 => new( "three", value ),
                4 => new( "four", value ),
                5 => new( "five", value ),
                6 => new( "six", value ),
                7 => new( "seven", value ),
                8 => new( "eight", value ),
                9 => new( "nine", value ),
                _ => null,
            };
        }
    }

    internal class TeensNode : NumberNode
    {
        private TeensNode( string word, long value ) : base( word, value ) { }

        public static TeensNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "ten" => new( word, 10 ),
                "eleven" => new( word, 11 ),
                "tweleve" => new( word, 12 ),
                "thriteen" => new( word, 13 ),
                "fourteen" => new( word, 14 ),
                "fifteen" => new( word, 15 ),
                "sixteen" => new( word, 16 ),
                "seventeen" => new( word, 17 ),
                "eighteen" => new( word, 18 ),
                "nineteen" => new( word, 19 ),
                _ => null,
            };
        }

        public static TeensNode? FromValue( long value )
        {
            return value switch {
                10 => new( "ten", value ),
                11 => new( "eleven", value ),
                12 => new( "tweleve", value ),
                13 => new( "thriteen", value ),
                14 => new( "fourteen", value ),
                15 => new( "fifteen", value ),
                16 => new( "sixteen", value ),
                17 => new( "seventeen", value ),
                18 => new( "eighteen", value ),
                19 => new( "nineteen", value ),
                _ => null,
            };
        }
    }

    internal class TensNode : NumberNode
    {
        private TensNode( string word, long value ) : base( word, value ) { }

        public static TensNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "twenty" => new( word, 20 ),
                "thrity" => new( word, 30 ),
                "forty" => new( word, 40 ),
                "fifty" => new( word, 50 ),
                "sixty" => new( word, 60 ),
                "seventy" => new( word, 70 ),
                "eighty" => new( word, 80 ),
                "ninety" => new( word, 90 ),
                _ => null,
            };
        }

        public static TensNode? FromValue( long value, out long remaining )
        {
            remaining = value % 10;
            value = value / 10 * 10;

            return value switch {
                20 => new( "twenty", value ),
                30 => new( "thrity", value ),
                40 => new( "forty", value ),
                50 => new( "fifty", value ),
                60 => new( "sixty", value ),
                70 => new( "seventy", value ),
                80 => new( "eighty", value ),
                90 => new( "ninety", value ),
                _ => null,
            };
        }
    }

    internal class HundredNode : NumberNode
    {
        private HundredNode( string word, long value ) : base( word, value ) { }

        public static HundredNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "hundred" => new( word, 100 ),
                _ => null,
            };
        }

        public static (SingleDigitNode, HundredNode)? FromValue( long value, out long remaining )
        {
            ArgumentOutOfRangeException.ThrowIfGreaterThanOrEqual( value, 1000 );

            long hundreds = value / 100;
            remaining = value % 100;

            if ( hundreds > 0 )
            {
                SingleDigitNode single = SingleDigitNode.FromValue( hundreds ) ?? throw new InvalidOperationException( );
                HundredNode hundred = new( "hundred", 100 );
                return (single, hundred);
            }
            else
            {
                return null;
            }
        }
    }

    internal class ModifierNode : NumberNode
    {
        private ModifierNode( string word, long value ) : base( word, value ) { }

        public static ModifierNode? FromWord( string word )
        {
            return word.ToLowerInvariant( ) switch {
                "thousand" => new( word, 1_000 ),
                "million" => new( word, 1_000_000 ),
                "billion" => new( word, 1_000_000_000 ),
                "trillion" => new( word, 1_000_000_000_000 ),
                "quadrillion" => new( word, 1_000_000_000_000_000 ),
                "quintillion" => new( word, 1_000_000_000_000_000_000 ),
                _ => null
            };
        }

        public static ModifierNode? FromValue( long value, out long group, out long remaining )
        {
            ArgumentOutOfRangeException.ThrowIfLessThanOrEqual( value, 0 );

            long order = GetOrderOfMagnitude( value );
            group = value / order;
            remaining = value % order;
            ModifierNode? node = order > 1 ? new( WordForValue( order ), order ) : null;

            return node;
        }

        private static long GetOrderOfMagnitude( long value )
        {
            ArgumentOutOfRangeException.ThrowIfLessThanOrEqual( value, 0 );

            long[] orders = [
                1_000_000_000_000_000_000,
                1_000_000_000_000_000,
                1_000_000_000_000,
                1_000_000_000,
                1_000_000,
                1_000,
                1,
            ];

            foreach ( long order in orders )
            {
                if ( value >= order )
                {
                    return order;
                }
            }

            throw new ArgumentException( "Value must be > 0", nameof( value ) );
        }

        private static string WordForValue( long value )
        {
            return value switch {
                1_000_000_000_000_000_000 => "quintillion",
                1_000_000_000_000_000 => "quadrillion",
                1_000_000_000_000 => "trillion",
                1_000_000_000 => "billion",
                1_000_000 => "million",
                1_000 => "thousand",
                _ => throw new ArgumentException( "Value must be exactly an order", nameof( value ) )
            };
        }
    }
}
