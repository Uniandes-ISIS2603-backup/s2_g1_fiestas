(function (ng) {
    var mod = ng.module("contratoModule");
    mod.constant("contratosContext", "api/contratos");
    mod.constant("horariosContext", "horarios");
    mod.controller('contratoUpdateCtrl', ['$scope', '$http', 'contratosContext', '$state', 'horariosContext', '$rootScope',
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
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, contratosContext, $state, horariosContext, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};
            $scope.horarios = [];

            var idContrato = $state.params.contratoId;

            //Consulto el contrato a editar.
            $http.get(contratosContext + '/' + idContrato).then(function (response) {
                var contrato = response.data;
                $scope.data.estado = contrato.estado;
                $scope.data.proveedor = contrato.proveedor;
                $scope.data.evento = contrato.evento;
                $scope.data.nombre = contrato.nombre;
                $scope.horario = contrato.horario;
                //Pendiente productos

            });

            /**
             * @ngdoc function
             * @name updateContrato
             * @methodOf contratos.controller:contratoUpdateCtrl
             * @description
             * Actualiza un contrato con la información del
             * $scope.
             */
            $scope.updateContrato = function () {
                $http.put(contratosContext + "/" + idContrato, $scope.data).then(function (response) {
                    //Contrato created successfully
                    for (i = 0; i < $scope.horarios.length; i++) {
                        $http.post(contratosContext + "/" + response.data.id + "/" + horariosContext, $scope.horarios[i]).then(function(response) {
                        });
                    }
                    $state.go('contratosList', {contratoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


