using System.ServiceModel;
using System.Collections.Generic;

namespace DepositAccount.Services
{
    [ServiceContract]
    public interface IDepotService
    {
        [OperationContract]
        List<Depot> GetDepotsByCompte(int idCompte);

        [OperationContract]
        Depot GetDepotById(int id);

        [OperationContract]
        Depot CreateDepot(Depot depot);

        [OperationContract]
        bool DepotExists(int id);
    }
}