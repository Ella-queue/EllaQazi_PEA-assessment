using System;
using System.Collections.Generic;

namespace W26HVKTEAM4.Models;

public partial class Vaccination
{
    public int VaccinationId { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<PetVaccination> PetVaccinations { get; set; } = new List<PetVaccination>();
}
