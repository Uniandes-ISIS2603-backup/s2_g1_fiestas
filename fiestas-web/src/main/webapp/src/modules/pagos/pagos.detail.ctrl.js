(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.controller('pagoDetailCtrl', ['$scope', '$http','eventosContext','$state','pagoContext','clientesContext',
        function ($scope, $http,eventosContext,$state, pagoContext,clientesContext) {

            if (($state.params.pagoId !== undefined) && ($state.params.pagoId !== null)) {
                $http.get(clientesContext + '/' + $state.params.clienteId + '/' +eventosContext+'/'+$state.params.eventoId+'/'+pagoContext+'/'+$state.params.pagoId).then(function (response) {
                    $scope.currentPago = response.data;
            });
        }
    }
    ]);
}
)(window.angular);