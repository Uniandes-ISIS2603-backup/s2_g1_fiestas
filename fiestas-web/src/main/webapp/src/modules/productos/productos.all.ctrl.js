(function (ng) {
    var mod = ng.module("productosModule");
    mod.controller('productosAllCtrl', ['$scope', '$http', '$state', '$rootScope',
        function ($scope, $http, $state, $rootScope) {
            console.log($rootScope);
            $http.get("api/productos").then(function (response) {
                $rootScope.productosRecords = response.data;
            });
            
        }
    ]);
}
        )(window.angular);