(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.controller('valoracionNewCtrl', ['$scope', '$http', 'valoracionContext', '$state', '$filter',
        
        /**
         * @ngdoc controller
         * @name valoraciones.controller:valoracionNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Valoraciones. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Valoraciones en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de valoraciones en el Backend.
         */
       
       function ($scope, $http, valoracionContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            $scope.data = {};
            
            /**
             * @ngdoc function
             * @name createEditorial
             * @methodOf valoraciones.controller:valoracionNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el autor.
             * @param {Object} autor Objeto con el nuevo autor.
             */
            
            $scope.createValoracion = function () {
                $http.post(valoracionContext, $scope.data).then(function (response) {
                    $state.go('valoracionesList', {valoracionId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);