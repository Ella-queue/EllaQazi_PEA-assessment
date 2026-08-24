using System.Text.RegularExpressions;

namespace W26HVKTEAM4.Models
{
    public class CustomerFormattingService
    {
        public string PhoneDisplay(string? p) {
            if (p != null) {
                string px = Regex.Replace(p, @"(\d{3})(\d{3)(\d{4})", "($1) $2-$3");
                return px;
            }
            return "";
        }
    }
}
