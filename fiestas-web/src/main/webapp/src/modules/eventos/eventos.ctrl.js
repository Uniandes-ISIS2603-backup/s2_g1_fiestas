(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "api/eventos");
    mod.controller('eventoCtrl', ['$scope', '$http', 'eventoContext',
        function ($scope, $http, eventoContext) {
            $http.get(eventoContext).then(function (response) {
                $scope.eventosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);