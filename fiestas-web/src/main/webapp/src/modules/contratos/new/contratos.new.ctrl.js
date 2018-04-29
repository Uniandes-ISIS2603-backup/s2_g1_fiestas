(function (ng) {
    var mod = ng.module("contratoModule");
    mod.constant("contratoContext", "api/contratos");
    mod.controller('contratoNewCtrl', ['$scope', '$http', 'contratoContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name contratos.controller:contratoNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Contratos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, contratoContext, $state, $rootScope) {
            $rootScope.edit = false;
            
            $scope.data = {};
            
             /**
             * @ngdoc function
             * @name createContrato
             * @methodOf contratos.controller:contratoNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el contrato.
             * @param {Object} autor Objeto con el nuevo autor.
             */
            $scope.createContrato = function () {
                $http.post(contratoContext, $scope.data).then(function (response) {
                    $state.go('contratosList', {contratoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);