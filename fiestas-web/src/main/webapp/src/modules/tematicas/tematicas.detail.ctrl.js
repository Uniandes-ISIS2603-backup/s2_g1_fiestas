(function (ng) {
    var mod = ng.module("tematicasModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicasDetailCtrl', ['$scope', '$http', 'tematicasContext', '$state', '$filter',
        function ($scope, $http, tematicasContext, $state, $filter) {
            /**
             * @ngdoc controller
             * @name tematicas.controller:tematicaDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Tematicas. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} eventoContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Eventos en el Backend.
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             * @param {Object} tematicaContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Tematicas en el Backend.
             * *@param {Object} clientesContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Clientes en el Backend.
             */
            if (($state.params.tematicaID !== undefined) && ($state.params.tematicaID !== null)) {
                /**
                 * @ngdoc function
                 * @name getTematicaID
                 * @methodOf tematicas.controller:tematicaDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el evento por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del evento o API donde se puede consultar.
                 */
                $http.get(tematicasContext).then(function (response) {
                    $scope.tematicasRecords = response.data;
                    $scope.currentTematica = $filter('filter')($scope.tematicasRecords, {ID: $state.params.tematicaID}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);
