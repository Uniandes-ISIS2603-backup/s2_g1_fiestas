(function (ng) {
    var mod = ng.module("horarioModule");
    mod.constant("horarioContext", "horarios");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('horarioUpdateCtrl', ['$scope', '$http', 'contratosContext', '$state', 'horarioContext','$rootScope',
        /**
         * @ngdoc controller
         * @name contratos.controller:contratoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Contratos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} contratosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, contratosContext, $state, horarioContext,$rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idContrato = $state.params.contratoId;
            var idHorario = $state.params.horarioId;

            //Consulto el horario a editar.
            $http.get(contratosContext + '/' + idContrato + '/' + horarioContext + '/' + idHorario).then(function (response) {
                var horario = response.data;
                $scope.data.id = horario.id;
                $scope.data.realizado = horario.realizado;
                $scope.data.metodoHorario = horario.metodoHorario;
                $scope.data.estado = horario.estado;
                $scope.data.valor = horario.valor;
            });


            /**
             * @ngdoc function
             * @name updateHorario
             * @methodOf horarios.controller:horarioUpdateCtrl
             * @description
             * Actualiza un horario
             */
            $scope.updateHorario = function () {
                $http.put(contratosContext + '/' + idContrato + '/' + horarioContext + '/' + idHorario, $scope.data).then(function (response) {
                    //Horario updated successfully
                    $state.go('horariosList', {horarioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);

