(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.controller('valoracionCtrl', ['$scope', '$http', 'valoracionContext',
        function ($scope, $http, valoracionContext) {
            $http.get('data/valoraciones.json').then(function (response) {
                $scope.valoracionesRecords = response.data;
            });
        }
    ]);
}
)(window.angular);