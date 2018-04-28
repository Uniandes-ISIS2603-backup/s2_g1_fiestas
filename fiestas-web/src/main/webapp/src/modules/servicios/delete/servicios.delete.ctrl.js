(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("serviciosContext", "api/servicio");
    mod.controller('servicioDeleteCtrl', ['$scope', '$http', 'serviciosContext', '$state',
        function ($scope, $http, serviciosContext, $state) {
            var idServicio = $state.params.servicioId;
            $scope.deleteServicio = function () {
                $http.delete(serviciosContext + '/' + idServicio, {}).then(function (response) {
                    $state.go('serviciosList', {servicioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);