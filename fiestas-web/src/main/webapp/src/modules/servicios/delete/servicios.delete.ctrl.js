(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("serviciosContext", "api/servicio");
    mod.controller('servicioDeleteCtrl', ['$scope', '$http', 'serviciosContext', '$state',
        /**
         * @ngdoc controller
         * @name servicios.controller:servicioDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Servicios. 
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
        function ($scope, $http, servicioContext, $state) {
            var idServicio = $state.params.servicioId;
            /**
             * @ngdoc function
             * @name deleteServicio
             * @methodOf servicios.controller:servicioDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el servicio.
             * @param {String} id El ID del servicio a eliminar.
             */
            $scope.deleteServicio = function () {
                $http.delete(servicioContext + '/' + idServicio, {}).then(function (response) {
                    $state.go('serviciosList', {servicioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);