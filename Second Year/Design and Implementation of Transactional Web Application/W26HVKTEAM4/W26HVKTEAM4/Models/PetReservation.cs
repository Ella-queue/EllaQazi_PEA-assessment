using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class PetReservation
{
    public int PetReservationId { get; set; }

    [Range(1, int.MaxValue, ErrorMessage = "Please select a pet.")]
    public int PetId { get; set; }

    public int ReservationId { get; set; }

    public int? RunId { get; set; }

    [ValidateNever]
    public virtual ICollection<Medication> Medications { get; set; } = new List<Medication>();

    [ValidateNever]
    public virtual Pet Pet { get; set; } = null!;

    [ValidateNever]
    public virtual ICollection<PetReservationDiscount> PetReservationDiscounts { get; set; } = new List<PetReservationDiscount>();

    [ValidateNever]
    public virtual ICollection<PetReservationService> PetReservationServices { get; set; } = new List<PetReservationService>();

    [ValidateNever]
    public virtual Reservation Reservation { get; set; } = null!;

    [ValidateNever]
    public virtual Run? Run { get; set; }
}