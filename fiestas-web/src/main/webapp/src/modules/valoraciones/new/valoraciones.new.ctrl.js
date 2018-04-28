(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.controller('valoracionNewCtrl', ['$scope', '$http', 'valoracionContext', '$state', '$filter',
       
       function ($scope, $http, valoracionContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            $scope.data = {};
            $scope.createValoracion = function () {
                $http.post(valoracionContext, $scope.data).then(function (response) {
                    $state.go('valoracionesList', {valoracionId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);