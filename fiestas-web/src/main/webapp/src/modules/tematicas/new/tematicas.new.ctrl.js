(function (ng) {
    var mod = ng.module("tematicaModule");
    mod.constant("tematicaContext", "api/tematicas");
    mod.controller('tematicasNewCtrl', ['$scope', '$http', 'tematicaContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name tematicas.controller:tematicaNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Tematicas. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Tematicas en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, tematicaContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            $scope.data = {};
            
             /**
             * @ngdoc function
             * @name createTematica
             * @methodOf tematicas.controller:tematicaNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el tematica.
             * @param {Object} autor Objeto con el nuevo autor.
             */
            $scope.createTematica = function () {
                $http.post(tematicaContext, $scope.data).then(function (response) {
                    $state.go('tematicasList', {tematicaId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);