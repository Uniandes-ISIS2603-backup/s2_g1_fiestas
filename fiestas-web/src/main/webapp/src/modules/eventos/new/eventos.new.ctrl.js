(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "api/eventos");
    mod.controller('eventoNewCtrl', ['$scope', '$http', 'eventoContext', '$state', '$filter',
        function ($scope, $http, eventoContext, $state, $filter) {
            $rootScope.edit = false;
            $scope.data = {};
            $scope.createEditorial = function () {
                $http.post(eventoContext, $scope.data).then(function (response) {
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);