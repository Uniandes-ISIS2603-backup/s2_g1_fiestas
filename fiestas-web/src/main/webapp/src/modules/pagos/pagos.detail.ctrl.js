(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('pagoDetailCtrl', ['$scope', '$http','eventosContext','$state','pagoContext',
        function ($scope, $http,eventosContext,$state, pagoContext) {

            if (($state.params.pagoId !== undefined) && ($state.params.pagoId !== null)) {
                $http.get(eventosContext+'/'+$state.params.eventoId+'/'+pagoContext+'/'+$state.params.pagoId).then(function (response) {
                    $scope.currentPago = response.data;
            });
        }
    }
    ]);
}
)(window.angular);