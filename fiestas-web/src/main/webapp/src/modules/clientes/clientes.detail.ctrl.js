(function (ng) {
    var mod = ng.module("clienteModule");
    mod.constant("clienteContext", "api/clientes");
    mod.controller('clienteDetailCtrl', ['$scope', '$http', 'clienteContext', '$state', '$filter',
        function ($scope, $http, clienteContext, $state, $filter) {

            if (($state.params.clienteId !== undefined) && ($state.params.clienteId !== null)) {
                $http.get(clienteContext).then(function (response) {
                    $scope.clientesRecords = response.data;
                    $scope.currentSport = $filter('filter')($scope.clientesRecords, {id: $state.params.clienteId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);