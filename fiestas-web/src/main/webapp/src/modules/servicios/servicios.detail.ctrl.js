(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicios");
    mod.controller('servicioDetailCtrl', ['$scope', '$http', 'servicioContext', '$state', '$filter',
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
        function ($scope, $http, servicioContext, $state, $filter) {
                if (($state.params.servicioId !== undefined) && ($state.params.servicioId !== null)) {
                 /** 
                 * @ngdoc function
                 * @name getServicioID
                 * @methodOf servicios.controller:servicioDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el servicio por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del servicio o API donde se puede consultar.
                 */
                $http.get(servicioContext).then(function (response) {
                    $scope.serviciosRecords = response.data;
                    $scope.currentServicio = $filter('filter')($scope.serviciosRecords, {id: $state.params.servicioId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);