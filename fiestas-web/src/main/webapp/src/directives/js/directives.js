(function (ng) {
  var mod = angular.module('mainApp');
  
   mod.constant('template', 'src/directives/templates/');


    /**
     * @ngdoc directive
     * @name directive:moveLists
     * @scope
     * @priority 0
     * @restrict E
     * 
     * @param {array} selected Lista de items seleccionades
     * @param {array} available Lista de items posibles
     * 
     * @description
     * 
     * Crea una vista que permite intercambiar elementos entre dos arreglos
     * 
     * @example
     * <pre>
     *     <move-lists selected="selectedItems" available="availableItems"></move-lists>
     * </pre>
     */
    mod.directive('moveLists', ['template', function (tplDir) {
        return {
            scope: {
                selected: '=*',
                available: '=*'
            },
            restrict: 'E',
            templateUrl: tplDir + 'move-lists.tpl.html',
            controllerAs: '$ctrl',
            controller: ['$scope', function ($scope) {
                function move(src, dst, marked) {
                    // If selected is undefined, all records from src are moved to dst
                    if (!!marked) {
                        for (var i = 0; i < marked.length; i++) {
                            if (marked.hasOwnProperty(i)) {
                                var index = null;
                                for (var j = 0; j < src.length; j++) {
                                    if (src.hasOwnProperty(j)) {
                                        if (src[j].id === marked[i].id) {
                                            index = j;
                                            break;
                                        }
                                    }
                                }
                                if (index !== null) {
                                    dst.push(src.splice(index, 1)[0]);
                                }
                            }
                        }
                    } else {
                        dst.push.apply(dst, src);
                        src.splice(0, src.length);
                    }
                }

                move($scope.available, [], $scope.selected);
                $scope.selectedMarked = [];
                $scope.availableMarked = [];

                this.addSome = function () {
                    move($scope.available, $scope.selected, $scope.availableMarked);
                    $scope.availableMarked = [];
                };
                this.addAll = function () {
                    move($scope.available, $scope.selected);
                    $scope.availableMarked = [];
                };
                this.removeSome = function () {
                    move($scope.selected, $scope.available, $scope.selectedMarked);
                    $scope.selectedMarked = [];
                };
                this.removeAll = function () {
                    move($scope.selected, $scope.available);
                    $scope.selectedMarked = [];
                };
            }]
        };
    }]);
  
})(window.angular);


