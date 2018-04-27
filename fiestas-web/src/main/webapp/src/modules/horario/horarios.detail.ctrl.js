(function (ng) {
    var mod = ng.module("horarioModule");
    mod.constant("horarioContext", "horarios");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('horarioDetailCtrl', ['$scope', '$http','contratosContext','$state','horarioContext',
        function ($scope, $http,contratosContext,$state, horarioContext) {

            if (($state.params.horarioId !== undefined) && ($state.params.horarioId !== null)) {
                $http.get(contratosContext+'/'+$state.params.contratoId+'/'+horarioContext+'/'+$state.params.horarioId).then(function (response) {
                    $scope.currentHorario = response.data;
            });
        }
    }
    ]);
}
)(window.angular);