using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models
{
    public class CustomValidation
    {
        public sealed class CheckStartDate : ValidationAttribute
        {
            protected override ValidationResult IsValid(object value, ValidationContext validationContext)
            {
                if (value != null)
                {
                    DateTime date = Convert.ToDateTime(value);

                    if (date.CompareTo(DateTime.Now) >= 0)
                    {
                        return ValidationResult.Success;
                    }
                    else
                    {
                        return new ValidationResult("Date must be in the future or present");
                    }
                }
                else
                {
                        return ValidationResult.Success;
                }
            }
        }

        public sealed class CheckEndDate : ValidationAttribute
        {
            protected override ValidationResult IsValid(object value, ValidationContext validationContext)
            {
                if (value != null)
                {
                    if (DateTime.Compare(Convert.ToDateTime(value), DateTime.Now) > 0)
                    {
                        return ValidationResult.Success;
                    }
                    else
                    {
                        return new ValidationResult("Date must be in the future");
                    }
                }
                else
                {
                    return ValidationResult.Success;
                }
            }
        }

    }
}
