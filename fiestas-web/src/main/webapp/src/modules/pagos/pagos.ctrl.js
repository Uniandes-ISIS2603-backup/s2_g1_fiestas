(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "api/pagos");
    mod.controller('pagoCtrl', ['$scope', '$http', 'pagoContext',
        function ($scope, $http, pagoContext) {
            $http.get(pagoContext).then(function (response) {
                $scope.pagosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);