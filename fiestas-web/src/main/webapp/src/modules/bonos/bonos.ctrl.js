(function (ng) {
    var mod = ng.module("bonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosCtrl', ['$scope', '$http', 'bonosContext',
        function ($scope, $http, bonosContext) {
            $http.get(bonosContext).then(function (response) {
                $scope.bonosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);