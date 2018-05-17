(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicios");
    mod.constant("productosContext", "api/servicio");
    mod.controller('servicioDetailCtrl', ['$scope', '$http', 'servicioContext', 'productosContext', '$state', '$filter', '$rootScope',
        /**
         * @ngdoc controller
         * @name servicios.controller:servicioDetailCtrl
         * @description
         * Definición de un controlador auxiliar del módulo Servicios. 
         * Se crea el controlador con el cual se manejan las vistas de detalle
         * del módulo.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} serviciosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Servicios en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, servicioContext, productosContext, $state, $filter, $rootScope) {
            console.log($state.params.productoId);
            if (($state.params.productoId !== undefined) && ($state.params.productoId !== null)) {
                $scope.productoId = $state.params.productoId;
                $http.get(productosContext + '/' + $state.params.productoId + '/' + servicioContext).then(function (response) {
                    $rootScope.serviciosRecords = response.data;
                    $rootScope.currentServicio = $filter('filter')($rootScope.serviciosRecords, {ID: $state.params.servicioId}, true)[0];
                });
            }
        }
    ]);
}
        )(window.angular);