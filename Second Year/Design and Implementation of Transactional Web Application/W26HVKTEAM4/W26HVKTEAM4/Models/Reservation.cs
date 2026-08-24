using System;
using System.Collections.Generic;
using static W26HVKTEAM4.Models.CustomValidation;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class Reservation
{
    public int ReservationId { get; set; }
    //[CheckStartDate]
    [Required]
    [DataType(DataType.DateTime)]
    [Display(Name = "Start Date")]
    public DateOnly StartDate { get; set; }
    //[CheckEndDate]
    [Required]
    [DataType(DataType.DateTime)]
    [Display(Name = "End Date")]
    public DateOnly EndDate { get; set; }

    public decimal Status { get; set; }

    public virtual ICollection<PetReservation> PetReservations { get; set; } = new List<PetReservation>();

    public virtual ICollection<ReservationDiscount> ReservationDiscounts { get; set; } = new List<ReservationDiscount>();
}
