(function (ng) {
    var mod = ng.module("tematicaModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicaDeleteCtrl', ['$scope', '$http', 'tematicasContext', '$state',
        /**
         * @ngdoc controller
         * @name tematicas.controller:tematicaDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Tematicas. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Tematicas en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, tematicasContext, $state) {
            var idTematica = $state.params.tematicaId;
            /**
             * @ngdoc function
             * @name deleteTematica
             * @methodOf tematicas.controller:tematicaDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el tematica.
             * @param {String} id El ID del tematica a eliminar.
             */
            $scope.deleteTematica = function () {
                $http.delete(tematicasContext + '/' + idTematica, {}).then(function (response) {
                    $state.go('tematicasList', {tematicaId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);