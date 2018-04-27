(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "valoraciones");
    mod.constant("servicioContext", "api/servicio")
    mod.controller('valoracionCtrl', ['$scope', '$http', 'servicioContext', '$state', 'valoracionContext',
       function ($scope, $http, servicioContext, $state, valoracionContext) {
            $http.get(servicioContext + '/' + $state.params.servicioId + '/' + servicioContext).then(function (response) {
                $scope.valoracionesRecords = response.data;
            });
        }
    ]);
}
)(window.angular);