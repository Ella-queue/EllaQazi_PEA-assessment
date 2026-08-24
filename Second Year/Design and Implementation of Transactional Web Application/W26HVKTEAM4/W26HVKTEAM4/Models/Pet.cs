using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class Pet
{
    public int PetId { get; set; }
    [Required]
    [MaxLength(25, ErrorMessage = "Name must be up to 25 characters long")]

    public string Name { get; set; } = null!;
    [Required]

    public string Gender { get; set; } = null!;
    [MaxLength(50, ErrorMessage = "Breed must be up to 50 characters long")]

    public string? Breed { get; set; }
    [Display(Name  = "Birth Year")]
    public int? Birthyear { get; set; }

    public int HvkuserId { get; set; }
    [Display(Name = "Dog Size")]
    public string? DogSize { get; set; }

    public bool Climber { get; set; }

    public bool Barker { get; set; }
    [Display(Name = "Special Notes")]
    [DataType(DataType.MultilineText)]
    [MaxLength(200, ErrorMessage = "City must be up to 200 characters long")]
    public string? SpecialNotes { get; set; }

    public bool Sterilized { get; set; }

    public virtual Hvkuser Hvkuser { get; set; } = null!;

    public virtual ICollection<PetReservation> PetReservations { get; set; } = new List<PetReservation>();

    public virtual ICollection<PetVaccination> PetVaccinations { get; set; } = new List<PetVaccination>();
}
