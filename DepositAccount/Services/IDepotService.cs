using DepositAccount.Models;

namespace DepositAccount.Services
{
    public interface IDepotService
    {
        Task<List<Depot>> GetAllDepotsAsync();
        Task<Depot?> GetDepotByIdAsync(int id);
        Task<Depot> CreateDepotAsync(Depot depot);
        Task<Depot?> UpdateDepotAsync(int id, Depot depot);
        Task<bool> DeleteDepotAsync(int id);
        Task<List<Depot>> GetDepotsByCompteAsync(int idCompteCourant);
    }
}