(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.controller('valoracionDetailCtrl', ['$scope', '$http', 'valoracionContext', '$state', '$filter',
        function ($scope, $http, valoracionContext, $state, $filter) {

            if (($state.params.valoracionId !== undefined) && ($state.params.valoracionId !== null)) {
                $http.get('data/valoraciones.json').then(function (response) {
                    $scope.valoracionesRecords = response.data;
                    $scope.currentServicio = $filter('filter')($scope.valoracionesRecords, {id: $state.params.valoracionId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);