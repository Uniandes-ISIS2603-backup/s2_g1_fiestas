(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "api/eventos");
    mod.controller('eventoDetailCtrl', ['$scope', '$http', 'eventoContext', '$state', '$filter',
        function ($scope, $http, eventoContext, $state, $filter) {

            if (($state.params.eventoId !== undefined) && ($state.params.eventoId !== null)) {
                $http.get(eventoContext).then(function (response) {
                    $scope.eventosRecords = response.data;
                    $scope.currentEvento = $filter('filter')($scope.eventosRecords, {id: $state.params.eventoId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);