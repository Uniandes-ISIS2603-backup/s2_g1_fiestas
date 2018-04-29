(function (ng) {
    var mod = ng.module("contratoModule");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('contratoDeleteCtrl', ['$scope', '$http', 'contratosContext', '$state',
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
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, contratosContext, $state) {
            var idContrato = $state.params.contratoId;
            /**
             * @ngdoc function
             * @name deleteContrato
             * @methodOf contratos.controller:contratoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el contrato.
             * @param {String} id El ID del contrato a eliminar.
             */
            $scope.deleteContrato = function () {
                $http.delete(contratosContext + '/' + idContrato, {}).then(function (response) {
                    $state.go('contratosList', {contratoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);