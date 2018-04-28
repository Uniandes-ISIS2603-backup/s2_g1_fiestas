(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("servicioContext", "api/servicio");
    mod.controller('servicioNewCtrl', ['$scope', '$http', 'servicioContext', '$state', '$filter',
         /**
         * @ngdoc controller
         * @name servicios.controller:servicioNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Servicios. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} serviciosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Servicios en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} serviciosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de servicios en el Backend.
         */
       
       function ($scope, $http, servicioContext, $state, $rootScope) {
            $rootScope.edit = false;
            /**
             * @ngdoc function
             * @name createEditorial
             * @methodOf servicios.controller:servicioNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el autor.
             * @param {Object} autor Objeto con el nuevo autor.
             */
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