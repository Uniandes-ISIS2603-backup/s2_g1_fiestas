(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.constant("productosContext", "api/productos");
    mod.controller('valoracionesCtrl', ['$scope', '$http','valoracionContext','productosContext', '$state', '$filter','$rootScope',
         /**
         * @ngdoc controller
         * @name valoracions.controller:valoracionCtrl
         * @description
         * Definición del controlador de Angular del módulo Valoraciones. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Valoraciones en el Backend.
         */
       function ($scope, $http, valoracionContext, productosContext, $state, $filter, $rootScope) {
                //if (($state.params.productoId !== undefined) && ($state.params.productoId !== null)) 
                $scope.productoId = $state.params.productoId;
                $http.get(productosContext + '/' + $state.params.productoId + '/' + valoracionContext).then(function (response) {
                $rootScope.valoracionesRecords = response.data;
                $rootScope.currentValoracion = $filter('filter')($rootScope.valoracionesRecords, {id: $state.params.valoracionId}, true)[0];
            
            });
        }
    ]);
}
)(window.angular);