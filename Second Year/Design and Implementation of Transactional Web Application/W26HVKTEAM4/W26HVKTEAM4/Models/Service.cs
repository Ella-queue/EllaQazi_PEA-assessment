using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace W26HVKTEAM4.Models;

public partial class Service
{
    public int ServiceId { get; set; }

    public string ServiceDescription { get; set; } = null!;

    public virtual ICollection<DailyRate> DailyRates { get; set; } = new List<DailyRate>();

    public virtual ICollection<PetReservationService> PetReservationServices { get; set; } = new List<PetReservationService>();
}
