using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
namespace W26HVKTEAM4.Models;

public partial class Hvkuser
{
    public int HvkuserId { get; set; }
    [Required]
    [Display(Name = "First Name")]
    [MaxLength(25, ErrorMessage = "First Name must be up to 25 characters long")]
    public string FirstName { get; set; } = null!;
    [Required]
    [Display(Name = "Last Name")]
    [MaxLength(25, ErrorMessage = "Last Name must be up to 25 characters long")]
    public string LastName { get; set; } = null!;

    [Required]
    [DataType(DataType.EmailAddress)]
    [EmailAddress]
    [MaxLength(50, ErrorMessage = "Email must be up to 50 characters long")]
    [Display(Name = "Email Address")]
    public string Email { get; set; } = null!;

    [Required]
    [DataType(DataType.Password)]
    [MaxLength(50, ErrorMessage = "Password must be up to 50 characters long")]
    public string Password { get; set; } = null!;
    [Required]
    [DataType(DataType.Password)]
    [MaxLength(50)]
    [Compare(nameof(Password), ErrorMessage = "Password does not match")]
    [NotMapped]
    public string ConfirmPassword { get; set; } = null!;

    public string? Street { get; set; }
    [MaxLength(25, ErrorMessage = "City must be up to 25 characters long")]

    public string? City { get; set; }

    public string? Province { get; set; }
    [Display(Name = "Postal Code")]
    [MaxLength(6, ErrorMessage = "Postal Code must be up to 6 characters long")]
    [RegularExpression("^[A-Za-z][0-9][A-Za-z][A-Za-z][0-9][A-Za-z]$", ErrorMessage = "The Postal Code must be in the format A1AA1A")]
    public string? PostalCode { get; set; }
    [Display(Name = "Phone Number")]
    [DataType(DataType.PhoneNumber)]
    [Phone] //More explicit than Regex("^[0-9]*$")
    [MaxLength(10, ErrorMessage = "Phone Number must be up to 10 characters long")]
    public string? Phone { get; set; }
    [Display(Name = "Cell Phone Number")]
    [DataType(DataType.PhoneNumber)]
    [Phone]
    [MaxLength(10, ErrorMessage = "Cell Phone Number must be up to 10 characters long")]
    public string? CellPhone { get; set; }
    [Display(Name = "First Name")]
    [MaxLength(25, ErrorMessage = "Emergency Contact First Name must be up to 25 characters long")]
    public string? EmergencyContactFirstName { get; set; }
    [Display(Name = "Last Name")]
    [MaxLength(25, ErrorMessage = "Emergency Contact Last Name must be up to 25 characters long")]
    public string? EmergencyContactLastName { get; set; }
    [Display(Name = "Phone Number")]
    [DataType(DataType.PhoneNumber)]
    [Phone(ErrorMessage = "The Emergency Contact Phone Number field is not a valid phone number")]
    [MaxLength(10, ErrorMessage = "Emergency Contact Phone Number must be up to 10 characters long")]
    public string? EmergencyContactPhone { get; set; }
    [Required]
    public string UserType { get; set; } = null!;
    public virtual ICollection<Pet?>? Pets { get; set; } = new List<Pet?>();
}
