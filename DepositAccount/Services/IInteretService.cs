using DepositAccount.Models;

namespace DepositAccount.Services
{
    public interface IInteretService
    {
        Task<List<Interet>> GetAllInteretsAsync();
        Task<Interet?> GetInteretByIdAsync(int id);
        Task<Interet> CreateInteretAsync(Interet interet);
        Task<Interet?> UpdateInteretAsync(int id, Interet interet);
        Task<bool> DeleteInteretAsync(int id);
        Task<List<Interet>> GetInteretsByDepotAsync(int idDepot);
        Task<decimal> CalculateInteretAsync(decimal montant, decimal pourcentage, int duree);
    }
}