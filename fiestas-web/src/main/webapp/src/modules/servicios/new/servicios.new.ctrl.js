(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicio");
    mod.controller('servicioNewCtrl', ['$scope', '$http', 'servicioContext', '$state', '$filter',
       
       function ($scope, $http, servicioContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            $scope.data = {};
            $scope.createServicio = function () {
                $http.post(servicioContext, $scope.data).then(function (response) {
                    $state.go('serviciosList', {servicioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);