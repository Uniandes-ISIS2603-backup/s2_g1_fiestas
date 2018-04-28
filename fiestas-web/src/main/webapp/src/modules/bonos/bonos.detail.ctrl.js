(function (ng) {
    var mod = ng.module("bonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosDetailCtrl', ['$scope', '$http', 'bonosContext', '$state', '$filter',
        function ($scope, $http, bonosContext, $state, $filter) {
            if (($state.params.bonosId !== undefined) && ($state.params.bonosId !== null)) {
                $http.get(bonosContext).then(function (response) {
                    $scope.bonosRecords = response.data;
                    $scope.currentBonos = $filter('filter')($scope.bonosRecords, {id: $state.params.bonosId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);