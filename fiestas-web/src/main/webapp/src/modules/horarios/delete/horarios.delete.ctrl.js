(function (ng) {
    var mod = ng.module("horarioModule");
    mod.constant("horarioContext", "horarios");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('horarioDeleteCtrl', ['$scope', '$http', 'contratosContext', '$state', 'horarioContext',
        /**
         * @ngdoc controller
         * @name contratos.controller:contratoDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Contratos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} contratosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} horarioContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Horarios en el Backend.
         */
        function ($scope, $http, contratosContext, $state, horarioContext) {
            var idContrato = $state.params.contratoId;
            var idHorario = $state.params.horarioId;
            /**
             * @ngdoc function
             * @name deleteHorario
             * @methodOf horarios.controller:contratoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar un horario.
             * @param {String} id El ID del horario a eliminar.
             */
            $scope.deleteHorario = function () {
                $http.delete(contratosContext + '/' + idContrato + '/' + horarioContext + '/' + idHorario, {}).then(function (response) {
                    $state.go('horariosList', {horarioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);