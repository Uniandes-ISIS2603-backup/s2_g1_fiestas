(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicios");
    mod.controller('servicioCtrl', ['$scope', '$http', 'servicioContext',
        function ($scope, $http, servicioContext) {
            $http.get('data/servicios.json').then(function (response) {
                $scope.serviciosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);