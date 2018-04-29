(function (ng) {
    var mod = ng.module("horarioModule");
    mod.constant("horarioContext", "horarios");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('horarioNewCtrl', ['$scope', '$http', 'contratosContext', '$state', 'horarioContext', '$rootScope',
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
         * @param {Object} contratosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         *@param {Object} horarioContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Horario en el Backend.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, contratosContext, $state, horarioContext, $rootScope) {
            $rootScope.edit = false;

            $scope.data = {};

            /**
             * @ngdoc function
             * @name createHorario
             * @methodOf horarios.controller:horarioNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el horario.
             */
            $scope.createHorario = function () {
                $http.post(contratosContext+'/'+$state.params.contratoId+'/'+horarioContext, $scope.data).then(function (response) {
                    $state.go('horariosList', {horarioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);