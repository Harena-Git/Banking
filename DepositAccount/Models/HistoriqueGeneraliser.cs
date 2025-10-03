using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DepositAccount.Models
{
    public class HistoriqueGeneraliser
    {
        [Key]
        public int IdHistorique { get; set; }
        
        [Required]
        public DateTime DateTransactionHistorique { get; set; }
        
        [Required]
        [Column(TypeName = "decimal(15,2)")]
        public decimal MontantJoueeHistorique { get; set; }
        
        [StringLength(50)]
        public string? TypeTransaction { get; set; }
    }
}