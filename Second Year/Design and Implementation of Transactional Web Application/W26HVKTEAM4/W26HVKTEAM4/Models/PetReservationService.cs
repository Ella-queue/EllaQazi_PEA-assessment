using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class PetReservationService
{
    public int PetReservationId { get; set; }

    [Range(1, int.MaxValue, ErrorMessage = "Please select a service.")]

    public int ServiceId { get; set; }

    public string? NullHelper { get; set; }

    [ValidateNever]

    public virtual PetReservation PetReservation { get; set; } = null!;

    [ValidateNever]

    public virtual Service Service { get; set; } = null!;
}
