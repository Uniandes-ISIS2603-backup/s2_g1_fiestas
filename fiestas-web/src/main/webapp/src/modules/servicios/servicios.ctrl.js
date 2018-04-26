(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicio");
    mod.controller('servicioCtrl', ['$scope', '$http', 'servicioContext',
        function ($scope, $http, servicioContext) {
            $http.get(servicioContext).then(function (response) {
                $scope.serviciosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);