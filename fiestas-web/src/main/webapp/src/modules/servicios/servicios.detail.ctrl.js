(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicios");
    mod.controller('servicioDetailCtrl', ['$scope', '$http', 'servicioContext', '$state', '$filter',
        function ($scope, $http, servicioContext, $state, $filter) {

            if (($state.params.servicioId !== undefined) && ($state.params.servicioId !== null)) {
                $http.get('data/servicios.json').then(function (response) {
                    $scope.serviciosRecords = response.data;
                    $scope.currentServicio = $filter('filter')($scope.serviciosRecords, {id: $state.params.servicioId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);