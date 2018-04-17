(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "api/pagos");
    mod.controller('pagoDetailCtrl', ['$scope', '$http', 'pagoContext', '$state', '$filter',
        function ($scope, $http, pagoContext, $state, $filter) {

            if (($state.params.pagoId !== undefined) && ($state.params.pagoId !== null)) {
                $http.get(pagoContext + '/' + $state.params.pagoId).then(function (response) {
                    $scope.pagosRecords = response.data;
                    $scope.currentPago = $filter('filter')($scope.pagosRecords, {id: $state.params.pagoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);