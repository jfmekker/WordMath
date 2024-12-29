using System.Net.Sockets;

namespace WordMath.ConsoleApp
{
    internal class Program
    {
        public static void Main( string[] args )
        {
            Console.WriteLine( "Welcome to WordMath!" );

            bool running = true;
            while ( running )
            {
                Console.WriteLine( "Please enter a number:" );
                string? input = Console.ReadLine( );
                if ( input == null || input.ToLowerInvariant( ) == "exit" )
                {
                    Console.WriteLine( "Goodbye!\n" );
                    running = false;
                }
                else if ( input.Length > 0 && (char.IsDigit( input[0] ) || input[0] is '-' or '.') )
                {
                    try
                    {
                        //input = input.Replace( ",", "" );
                        long value = long.Parse( input );
                        Number number = new( value );
                        Console.WriteLine( $"= {number}\n" );
                    }
                    catch ( Exception ex )
                    {
                        Console.WriteLine( $"Failed: {ex.Message}\n" );
                    }
                }
                else
                {
                    try
                    {
                        Number number = new( input );
                        Console.WriteLine( $"= {number.Value}\n" );
                    }
                    catch ( Exception ex )
                    {
                        Console.WriteLine( $"Failed: {ex.Message}\n" );
                    }
                }
            }
        }
    }
}
