using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class Medication
{
    public int MedicationId { get; set; }
    [Display(Name = "Name")]
    [MaxLength(50, ErrorMessage = "Name must be up to 50 characters long")]

    public string? Name { get; set; }
    [Display(Name = "Dosage")]
    [MaxLength(50, ErrorMessage = "Dosage must be up to 50 characters long")]

    public string? Dosage { get; set; }
    [Display(Name = "Special Instructions")]
    [MaxLength(50, ErrorMessage = "Special Instructions must be up to 50 characters long")]

    public string? SpecialInstruct { get; set; }
    [Display(Name = "End Date")]
    [DataType(DataType.Date)]

    public DateOnly? EndDate { get; set; }

    public int PetReservationId { get; set; }

    public virtual PetReservation PetReservation { get; set; } = null!;
}
