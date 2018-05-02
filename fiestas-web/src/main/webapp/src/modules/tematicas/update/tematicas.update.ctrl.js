(function (ng) {
    var mod = ng.module("tematicaModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.constant("pagosContext", "pagos");
    mod.controller('tematicaUpdateCtrl', ['$scope', '$http', 'tematicasContext', '$state', 'pagosContext', '$rootScope',
        /**
         * @ngdoc controller
         * @name tematicas.controller:tematicaUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Tematicas. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} tematicasContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Tematicas en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, tematicasContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            var idTematica = $state.params.tematicaId;

            //Consulto el tematica a editar.
            $http.get(tematicasContext + '/' + idTematica).then(function (response) {
                var tematica = response.data;
                $scope.data.servicios = tematica.servicios;
                $scope.data.descripcion = tematica.descripcion;
            });

            /**
             * @ngdoc function
             * @name updateTematica
             * @methodOf tematicas.controller:tematicaUpdateCtrl
             * @description
             * Actualiza un tematica con la información del
             * $scope.
             */
            $scope.updateTematica = function () {
                $http.put(tematicasContext + "/" + idTematica, $scope.data).then(function (response) {
                    //Tematica created successfully
                    $state.go('tematicasList', {tematicaId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


