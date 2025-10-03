using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DepositAccount.Models
{
    public class Depot
    {
        [Key]
        public int IdDepot { get; set; }
        
        [Required]
        public DateTime DateDepot { get; set; }
        
        [Required]
        [Column(TypeName = "decimal(15,2)")]
        public decimal MontantDepot { get; set; }
        
        [StringLength(50)]
        public string? TypeDepot { get; set; }
        
        public int IdHistorique { get; set; }
        public int IdCompteCourant { get; set; }
        
        // Navigation properties
        public virtual ICollection<Interet>? Interets { get; set; }
    }
}